package com.reliableplugins.oregenerator.runnable;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.GeneratorItem;

import java.util.Iterator;

public class GeneratorTask implements Runnable {

    private OreGenerator plugin;

    public GeneratorTask(OreGenerator plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {

        if (plugin.getGenerators().size() == 0) return;

        for (Iterator<GeneratorItem> iterator = plugin.getGenerators().iterator(); iterator.hasNext(); ) {
            GeneratorItem generator = iterator.next();
//            if (generator.getTaskType() == GeneratorItem.TaskType.FINISHED) {
//                iterator.remove();
//                continue;
//            }
//            generator.run();
        }
    }

}