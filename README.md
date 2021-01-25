# Guide to Setup Consul Server Client using Docker


## Generating openssl certs


```
# Check Domain 
server.amg.consul

# Generate consul ca cert
consul tls ca create

# Generate server certs
openssl req -new -newkey rsa:2048 -nodes -keyout server.amg.consul.key -out server.amg.consul.csr -subj '/CN=server.amg.consul'

# Self sign server cert
openssl x509 -req -in server.amg.consul.csr  -CA consul-agent-ca.pem -CAkey consul-agent-ca-key.pem -CAcreateserial -out server.amg.consul.crt

# View cert
openssl x509 -text -noout -in server.amg.consul.crt

# Generate cleint certs
openssl req -new -newkey rsa:2048 -nodes -keyout client.amg.consul.key -out client.amg.consul.csr -subj '/CN=client.amg.consul'

# Self sign client cert
openssl x509 -req -in client.amg.consul.csr -CA consul-agent-ca.pem -CAkey consul-agent-ca-key.pem -out client.amg.consul.crt

# Enable SAN
vim server-ext.cnf
echo 'subjectAltName=DNS:server.amg.consul,IP:0.0.0.0' > server-ext.cnf

# Update server cert for x509 extensions
openssl x509 -req -in server.amg.consul.csr -days 60 -CA consul-agent-ca.pem -CAkey consul-agent-ca-key.pem -CAcreateserial -out server.amg.consul.crt -extfile server-ext.cnf

# Update client cert for x509 extensions
openssl x509 -req -in client.amg.consul.csr -days 60 -CA consul-agent-ca.pem -CAkey consul-agent-ca-key.pem -out client.amg.consul.crt -extfile server-ext.cnf
```

## Useful Consul Commands
```
# View consul members
docker exec server-leade consul members

# View consul members if token is enabled
docker exec server-leade consul members -token=<<token>>
```

## Enable TLS consul
````
    "verify_incoming": false, #if UI remains to be accessible
    "verify_outgoing": true,
    "verify_server_hostname": true,
    "verify_incoming_rpc": true, # rpc will be encrypted
    "ca_file": "consul-agent-ca.pem",
    "key_file": "server.amg.consul.key",
    "cert_file": "server.amg.consul.crt",
    "auto_encrypt": {
        "allow_tls": true
    },
    "enable_script_checks": false,
    "disable_remote_exec": true
````

## Disable HTTP 
````
  "ports": {
    "https": 8501, 
    "http": -1 #disable http
  },
````

## Access UI from browser
````
Now the TLS is enabled and to access the UI . consul-client-cert.pem should be passed to request
Import consul-client-cert.pem to the browser
````