import http.server
from prometheus_client import start_http_server

class ServerHandler(http.server.BaseHTTPRequestHandler):
    def do_GET(self):
        self.send_response(200)
        self.end_headers()
        self.wfile.write(b"Hello world")

if __name__ == "__main__":
    start_http_server(8000)
    server = http.server.HTTPServer(('', 8001), ServerHandler)
    print("Http started on port 8001")
    server.serve_forever()