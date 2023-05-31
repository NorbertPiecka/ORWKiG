FROM prom/prometheus

# Belowe custom config for prometheus
ADD prometheus.yml /etc/prometheus/

# docker build -t prom .
# docker run -p 9090:9090 prom