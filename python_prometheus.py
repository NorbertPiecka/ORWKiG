import psutil
from prometheus_client import start_http_server, Gauge, Counter
from time import sleep

cpu_usage_metric = Gauge('cpu_usage', 'CPU usage percentage')
memory_usage_metric = Gauge('memory_usage', 'Memory usage percentage')
seconds_since_start_counter = Counter('seconds_since_start', 'Number of seconds since start')

def collect_metrics():
    cpu_usage = psutil.cpu_percent(interval=1)
    memory_usage = psutil.virtual_memory().used
    cpu_usage_metric.set(cpu_usage)
    memory_usage_metric.set(memory_usage)

def main():
    start_http_server(8000)
    print("Http started on port 8001")
    while True:     
        try:
            collect_metrics()
            seconds_since_start_counter.inc()
            sleep(1)
        except KeyboardInterrupt:
            break

if __name__ == "__main__":
    main()