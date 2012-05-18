/*
 * Copyright 2012
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

package org.terasology.logic.newWorld;

import org.terasology.math.Vector3i;

/**
 * @author Immortius
 */
public interface NewChunkGenerator {

    public void setWorldSeed(String seed);

    public void setWorldBiomeProvider(WorldBiomeProvider biomeProvider);

    /**
     * Generate the local contents of a chunk. This should be purely deterministic from the chunk contents, chunk
     * position and world seed - should not depend on external state or other data.
     * @param chunk
     */
    public void generateChunk(NewChunk chunk);

    /**
     * Generates any chunk content that can overlap surrounding chunks. At this point the
     * chunk is connected to the world, and all changes must be made through the world itself.
     * @param pos
     * @param world
     */
    public void postProcessChunk(Vector3i pos, WorldProvider world);
}
