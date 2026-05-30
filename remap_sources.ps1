$MappingsPath = Join-Path $env:TEMP "mappings\mappings.tiny"
$SrcRoot = Join-Path $PSScriptRoot "src\main\java"

$classMap = @{}
$fieldMap = @{}
$methodMap = @{}
$currentClass = $null

Get-Content $MappingsPath -Encoding UTF8 | ForEach-Object {
    $line = $_.TrimStart("`t")
    if ($line -match '^c\t') {
        $parts = $line -split "`t"
        if ($parts.Length -ge 3) {
            $inter = $parts[1]
            $named = $parts[2]
            if ($inter -like 'net/minecraft/*') {
                $short = ($inter -split '/')[-1]
                if ($short -like 'class_*') {
                    $namedJava = $named.Replace('/', '.')
                    $classMap[$short] = $namedJava
                    $classMap[$inter.Replace('/', '.')] = $namedJava
                }
                $currentClass = ($inter -split '/')[-1]
            }
        }
    }
    elseif ($line -match '^f\t' -and $currentClass) {
        $parts = $line -split "`t"
        if ($parts.Length -ge 4 -and $parts[2] -like 'field_*') {
            $fieldMap["$currentClass|$($parts[2])"] = $parts[3]
        }
    }
    elseif ($line -match '^m\t' -and $currentClass) {
        $parts = $line -split "`t"
        if ($parts.Length -ge 4 -and $parts[2] -like 'method_*') {
            $methodMap["$currentClass|$($parts[2])"] = $parts[3]
        }
    }
}

$allFields = $fieldMap.Values | Sort-Object Length -Descending -Unique
$allMethods = $methodMap.Values | Sort-Object Length -Descending -Unique
$sortedClassKeys = $classMap.Keys | Where-Object { $_ -like 'class_*' } | Sort-Object Length -Descending

Get-ChildItem $SrcRoot -Filter *.java -Recurse | ForEach-Object {
    $text = [IO.File]::ReadAllText($_.FullName)
    $orig = $text

    foreach ($key in $sortedClassKeys) {
        $named = $classMap[$key]
        $text = $text.Replace("net.minecraft.$key", $named)
        $simple = ($named -split '\.')[-1]
        $text = [regex]::Replace($text, "\b$([regex]::Escape($key))\b", $simple)
    }

    $seenFields = @{}
    foreach ($entry in $fieldMap.GetEnumerator()) {
        $fname = ($entry.Key -split '\|')[1]
        if (-not $seenFields.ContainsKey($fname)) {
            $seenFields[$fname] = $entry.Value
        }
    }
    foreach ($fname in ($seenFields.Keys | Sort-Object Length -Descending)) {
        $text = $text.Replace($fname, $seenFields[$fname])
    }
    $seenMethods = @{}
    foreach ($entry in $methodMap.GetEnumerator()) {
        $mname = ($entry.Key -split '\|')[1]
        if (-not $seenMethods.ContainsKey($mname)) {
            $seenMethods[$mname] = $entry.Value
        }
    }
    foreach ($mname in ($seenMethods.Keys | Sort-Object Length -Descending)) {
        $text = $text.Replace($mname, $seenMethods[$mname])
    }

    if ($text -ne $orig) {
        [IO.File]::WriteAllText($_.FullName, $text)
        Write-Host "Remapped $($_.Name)"
    }
}

Write-Host "Done. Classes: $($classMap.Count) Fields: $($fieldMap.Count) Methods: $($methodMap.Count)"
