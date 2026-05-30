$files = Get-ChildItem "$PSScriptRoot\src\main\java" -Filter *.java -Recurse
$replacements = @(
    @('import net.minecraft.world.item.ItemLike;', 'import net.minecraft.world.level.ItemLike;'),
    @('import net.minecraft.world.level.chunk.ChunkSection;', 'import net.minecraft.world.level.chunk.LevelChunkSection;'),
    @('ChunkSection', 'LevelChunkSection'),
    @('.getBlockPos()', '.blockPosition()'),
    @('.age', '.tickCount'),
    @('.getPlayers()', '.players()'),
    @('.getUuid()', '.getUUID()'),
    @('.getStartX()', '.getMinBlockX()'),
    @('.getStartZ()', '.getMinBlockZ()'),
    @('.getEndX()', '.getMaxBlockX()'),
    @('.getEndZ()', '.getMaxBlockZ()'),
    @('.getBottomY()', '.getMinY()'),
    @('.getSectionArray()', '.getSections()'),
    @('.toImmutable()', '.immutable()'),
    @('.isChunkLoaded(', '.hasChunk('),
    @('.getLightLevel(', '.getBrightness('),
    @('class_19232.x', 'class_19232.x()'),
    @('class_19232.z', 'class_19232.z()'),
    @('object2.x', 'object2.x()'),
    @('object2.z', 'object2.z()'),
    @('.add((Toast)', '.addToast((Toast)'),
    @('(ItemLike)', ''),
    @('new ItemStack((ItemLike)', 'new ItemStack(')
)

foreach ($file in $files) {
    $text = [IO.File]::ReadAllText($file.FullName)
    $orig = $text
    foreach ($pair in $replacements) {
        $text = $text.Replace($pair[0], $pair[1])
    }
    if ($text -ne $orig) {
        [IO.File]::WriteAllText($file.FullName, $text)
        Write-Host "API fix: $($file.Name)"
    }
}
