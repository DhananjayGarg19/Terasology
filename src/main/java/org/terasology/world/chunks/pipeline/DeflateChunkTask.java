/*
 * Copyright 2013 Moving Blocks
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

package org.terasology.world.chunks.pipeline;

import org.terasology.math.Vector3i;
import org.terasology.world.chunks.Chunk;
import org.terasology.world.chunks.ChunkProvider;
import org.terasology.world.chunks.internal.GeneratingChunkProvider;

/**
 * @author Immortius
 */
public class DeflateChunkTask extends AbstractChunkTask {

    public DeflateChunkTask(ChunkGenerationPipeline pipeline, Vector3i position, GeneratingChunkProvider provider) {
        super(pipeline, position, provider);
    }

    @Override
    public void enact() {
        Chunk chunk = getProvider().getChunkForProcessing(getPosition());
        if (chunk != null) {
            chunk.deflate();
        }
    }
}
