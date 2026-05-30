#!/usr/bin/env python3
"""Remap intermediary (class_*, field_*, method_*) names to Yarn named mappings."""
import os
import re
from pathlib import Path

MAPPINGS = Path(os.environ.get("YARN_MAPPINGS", os.path.join(os.environ.get("TEMP", "/tmp"), "mappings", "mappings.tiny")))
SRC = Path(__file__).parent / "src" / "main" / "java"

class_mappings = {}  # class_1799 -> net.minecraft.item.ItemStack
field_mappings = {}   # (owner_intermediary, field_8137) -> glowstone
method_mappings = {}  # (owner_intermediary, method_24515) -> getBlockPos

current_class = None

with MAPPINGS.open(encoding="utf-8") as f:
    for line in f:
        if not line.strip() or line.startswith("tiny\t"):
            continue
        parts = line.rstrip("\n").split("\t")
        kind = parts[0]
        if kind == "c" and len(parts) >= 3:
            inter, named = parts[1], parts[2]
            if inter.startswith("net/minecraft/"):
                short = inter.split("/")[-1]
                if short.startswith("class_"):
                    named_java = named.replace("/", ".")
                    class_mappings[short] = named_java
                    class_mappings[inter.replace("/", ".")] = named_java
            current_class = inter.split("/")[-1] if inter.startswith("net/minecraft/") else None
        elif kind == "f" and len(parts) >= 4 and current_class:
            desc, inter_name, named_name = parts[1], parts[2], parts[3]
            if inter_name.startswith("field_"):
                field_mappings[(current_class, inter_name)] = named_name
        elif kind == "m" and len(parts) >= 4 and current_class:
            desc, inter_name, named_name = parts[1], parts[2], parts[3]
            if inter_name.startswith("method_"):
                method_mappings[(current_class, inter_name)] = named_name

# Sort by length descending for safe replacement
sorted_classes = sorted(class_mappings.keys(), key=len, reverse=True)

def remap_file(path: Path) -> None:
    text = path.read_text(encoding="utf-8")
    original = text

    # Replace full net.minecraft.class_XXX imports and references
    for inter in sorted_classes:
        if not inter.startswith("class_"):
            continue
        named = class_mappings[inter]
        text = text.replace(f"net.minecraft.{inter}", named)
        # Inner classes: class_2338$class_2339 -> BlockPos$Mutable
        text = re.sub(
            rf"\b{re.escape(inter)}\.({inter}_\w+)\b",
            lambda m: named + "." + class_mappings.get(m.group(1), m.group(1).replace("class_", "")),
            text,
        )
        text = re.sub(rf"\b{re.escape(inter)}\b", named.split(".")[-1] if "." in named else named, text)

    # Replace field_* and method_* globally using all known mappings (best-effort)
    for (_, field_name), named in sorted(field_mappings.items(), key=lambda x: -len(x[0][1])):
        text = re.sub(rf"\b{re.escape(field_name)}\b", named, text)
    for (_, method_name), named in sorted(method_mappings.items(), key=lambda x: -len(x[0][1])):
        text = re.sub(rf"\b{re.escape(method_name)}\b", named, text)

    if text != original:
        path.write_text(text, encoding="utf-8")
        print(f"Remapped {path}")

for java in SRC.rglob("*.java"):
    remap_file(java)

print(f"Classes: {len([k for k in class_mappings if k.startswith('class_')])}")
print(f"Fields: {len(field_mappings)}, Methods: {len(method_mappings)}")
