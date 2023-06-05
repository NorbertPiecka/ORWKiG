package pl.edu.pw;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.HTTPServer;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.lang.management.*;
import java.util.Objects;
import java.util.Scanner;

public class SimpleLoggingService {
    private static final Counter requestsCounter = Counter.build()
            .name("myapp_requests_total")
            .help("Total number of requests processed")
            .register();

    private static final Gauge cpuUsageMetric = Gauge.build()
            .name("cpu_usage")
            .help("CPU usage percentage")
            .register();

    private static final Gauge heapMemoryUsageMaxMetric = Gauge.build()
            .name("heap_memory_usage_max")
            .help("Heap memory usage max in bytes")
            .register();

    private static final Gauge heapMemoryUsageUsedMetric = Gauge.build()
            .name("heap_memory_usage_used")
            .help("Heap memory usage used in bytes")
            .register();

    public static void main(String[] args) throws IOException {
        String state;
        HTTPServer server = new HTTPServer(8002);
        do {
            int wordLength = RandomUtils.nextInt(0, 2);
            if (wordLength == 1) {
                generateCPUMetrics();
            } else {
                generateMemoryMetrics();
            }

            requestsCounter.inc();
            Scanner scanner = new Scanner(System.in);
            state = scanner.nextLine();
        } while (!Objects.equals(state, "stop"));
        server.close();
    }

    public static void generateCPUMetrics() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();

        // Check if CPU usage is supported on the current platform
        if (osBean instanceof com.sun.management.OperatingSystemMXBean sunOsBean) {
            // Get CPU usage as a percentage
            double cpuUsage = sunOsBean.getProcessCpuLoad() * 100;
            System.out.println("[CPU Usage]: " + cpuUsage + "%");
            cpuUsageMetric.set(cpuUsage);
        } else {
            System.out.println("CPU usage monitoring is not supported on this platform.");
        }

        // Get thread count
        int threadCount = threadBean.getThreadCount();
        System.out.println("[Thread Count]: " + threadCount);
    }

    public static void generateMemoryMetrics() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

        // Get the heap memory usage
        MemoryUsage heapMemoryUsage = memoryBean.getHeapMemoryUsage();
        long usedHeapMemory = heapMemoryUsage.getUsed();
        long maxHeapMemory = heapMemoryUsage.getMax();

        System.out.println("[Heap Memory Usage]:");
        System.out.println("Used: " + usedHeapMemory + " bytes");
        System.out.println("Max: " + maxHeapMemory + " bytes");
        heapMemoryUsageUsedMetric.set(usedHeapMemory);
        heapMemoryUsageMaxMetric.set(maxHeapMemory);
    }
}