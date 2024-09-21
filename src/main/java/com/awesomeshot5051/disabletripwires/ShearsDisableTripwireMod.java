package com.awesomeshot5051.disabletripwires;

import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.block.TripWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ShearsDisableTripwireMod.MODID)
public class ShearsDisableTripwireMod {
    public static final String MODID = "shearstripwiremod";
    private static final Logger LOGGER = LogManager.getLogger();

    public ShearsDisableTripwireMod(IEventBus modEventBus) {
        // Register the setup method for modloading
        modEventBus.addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        NeoForge.EVENT_BUS.register(this);

        LOGGER.info("ShearsDisableTripwireMod constructor called!");
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Some preinit code
        LOGGER.info("ShearsDisableTripwireMod is setting up!");
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level world = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = world.getBlockState(pos);

        LOGGER.info("Right click block event fired!");

        if (state.getBlock() instanceof TripWireBlock && player.getMainHandItem().getItem() instanceof ShearsItem) {
            LOGGER.info("Player right-clicked a tripwire with shears!");
            if (!state.getValue(TripWireBlock.DISARMED)) {
                world.setBlock(pos, state.setValue(TripWireBlock.DISARMED, true), 3);
                LOGGER.info("Tripwire detached successfully!");
                event.setCanceled(true);
            }
        }
    }
}