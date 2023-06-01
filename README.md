1. Write down your ip in prometheus.yml -> for java and python application
2. Run: docker compose up
3. pip install prometheus_client
4. Start python app: py hello_world.py -> Metrics on port 8000 / Application on port 8001
5. Start java-app in Intelij -> Metrics on port 8002
6. Promethues data available here: localhost:9090/targets
