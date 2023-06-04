1. Write down your ip in prometheus.yml -> for java and python application
2. Run: docker compose up
3. pip install prometheus_client and pip install psutil
4. Start python app: py python_prometheus.py -> Metrics on port 8000
5. Start java-app in Intelij -> Metrics on port 8002
6. Promethues data available here: localhost:9090/targets
