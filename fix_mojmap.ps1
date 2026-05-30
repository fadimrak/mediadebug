$replacements = @(
    @('net.minecraft.block.entity', 'net.minecraft.world.level.block.entity'),
    @('net.minecraft.block', 'net.minecraft.world.level.block'),
    @('net.minecraft.item', 'net.minecraft.world.item'),
    @('net.minecraft.entity.player.PlayerEntity', 'net.minecraft.world.entity.player.Player'),
    @('net.minecraft.entity', 'net.minecraft.world.entity'),
    @('net.minecraft.world.chunk.WorldChunk', 'net.minecraft.world.level.chunk.LevelChunk'),
    @('net.minecraft.world.chunk', 'net.minecraft.world.level.chunk'),
    @('net.minecraft.world.World', 'net.minecraft.world.level.Level'),
    @('net.minecraft.world.LightType', 'net.minecraft.world.level.LightLayer'),
    @('net.minecraft.util.math.ChunkPos', 'net.minecraft.world.level.ChunkPos'),
    @('net.minecraft.util.math.BlockPos', 'net.minecraft.core.BlockPos'),
    @('net.minecraft.util.math.Vec3d', 'net.minecraft.world.phys.Vec3'),
    @('net.minecraft.util.math.Box', 'net.minecraft.world.phys.AABB'),
    @('net.minecraft.text.MutableText', 'net.minecraft.network.chat.MutableComponent'),
    @('net.minecraft.text.Text', 'net.minecraft.network.chat.Component'),
    @('net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget', 'net.minecraft.client.gui.screens.multiplayer.ServerSelectionList'),
    @('net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen', 'net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen'),
    @('net.minecraft.client.gui.screen.multiplayer', 'net.minecraft.client.gui.screens.multiplayer'),
    @('net.minecraft.client.toast', 'net.minecraft.client.gui.components.toasts'),
    @('net.minecraft.client.network.ClientPlayNetworkHandler', 'net.minecraft.client.multiplayer.ClientPacketListener'),
    @('MobSpawnerBlockEntity', 'SpawnerBlockEntity'),
    @('PlayerEntity', 'Player'),
    @('WorldChunk', 'LevelChunk'),
    @('LightType', 'LightLayer'),
    @('ItemConvertible', 'ItemLike'),
    @('Vec3d', 'Vec3'),
    @('BlockPos.class_2339', 'BlockPos.MutableBlockPos'),
    @('this.mc.world', 'this.mc.level'),
    @('LightLayer.BLOCK', 'LightLayer.BLOCK'),
    @(' Box ', ' AABB '),
    @('(Box)', '(AABB)'),
    @('new Box', 'new AABB')
)

Get-ChildItem "$PSScriptRoot\src\main\java" -Filter *.java -Recurse | ForEach-Object {
    $text = [IO.File]::ReadAllText($_.FullName)
    $orig = $text
    foreach ($pair in $replacements) {
        $text = $text.Replace($pair[0], $pair[1])
    }
    if ($text -ne $orig) {
        [IO.File]::WriteAllText($_.FullName, $text)
        Write-Host "Fixed $($_.Name)"
    }
}
