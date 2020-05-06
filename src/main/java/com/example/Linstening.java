package com.example;

import java.nio.file.*;
import java.util.List;

public class Linstening {
    public static void main(String[] args) {
        try
        {
            WatchService watchService = FileSystems.getDefault()
                    .newWatchService();
            Path path = Paths.get("D:\\sunsheen");
            // 注册监听器
            path.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE);
            while (true)
            {
                // 阻塞方式，消费文件更改事件
                List<WatchEvent<?>> watchEvents = watchService.take()
                        .pollEvents();
                System.out.println("无限监听、、、、、、");
                for (WatchEvent<?> watchEvent : watchEvents)
                {
                    System.out.printf("[%s]文件发生了[%s]事件。%n", watchEvent
                            .context(), watchEvent.kind());
                }
            }
        }
        catch (Exception e)
        {
        }
    }
}
