/*
 * Copyright 2016 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.gooeysQuests;

import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterMode;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.gooeysQuests.quests.dungeon.CopyBlockRegionResultEvent;
import org.terasology.logic.common.ActivateEvent;
import org.terasology.math.Region3i;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.In;
import org.terasology.world.WorldProvider;
import org.terasology.world.block.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the activation of the copyBlockRegionTool item.
 */
@RegisterSystem(RegisterMode.AUTHORITY)
public class CopyBlockRegionServerSystem extends BaseComponentSystem {

    @In
    private WorldProvider worldProvider;

    @ReceiveEvent
    public void onActivate(ActivateEvent event, EntityRef entity, CopyBlockRegionComponent copyBlockRegionComponent) {
        Region3i dungeonRegion = Region3i.createBounded(copyBlockRegionComponent.corner1,
                copyBlockRegionComponent.corner2);

        List<String> blockURIs = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        List<Block> blocks = new ArrayList<>();
        for (Vector3i position : dungeonRegion) {
            Block block = worldProvider.getBlock(position);
            blocks.add(block);
        }
        CopyBlockRegionResultEvent resultEvent = new CopyBlockRegionResultEvent(blocks);
        entity.send(resultEvent);
    }
}