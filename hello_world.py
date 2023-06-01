import http.server
from prometheus_client import start_http_server

class ServerHandler(http.server.BaseHTTPRequestHandler):
    def do_GET(self):
        self.send_response(200)
        self.end_headers()
        self.wfile.write(b"Hello PW")

if __name__ == "__main__":
    running = True
    user_var = ""
    start_http_server(8000)
    server = http.server.HTTPServer(('', 8001), ServerHandler)
    print("Http started on port 8001")
    while running:
        user_var = input()
        print(len(user_var))
        if len(user_var) > 10:
            running = False

    server.server_close()