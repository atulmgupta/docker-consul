# Guide to Setup Consul Server Client using Docker


## Generating openssl certs


```
# Domain 
server.<<datacenter>>.consul

# Generate consul ca cert
consul tls ca create

# Generate server certs
openssl req -new -newkey rsa:2048 -nodes -keyout server.<<datacenter>>.consul.key -out server.<<datacenter>>.consul.csr -subj '/CN=server.<<datacenter>>.consul'

# Self sign server cert
openssl x509 -req -in server.<<datacenter>>.consul.csr  -CA consul-agent-ca.pem -CAkey consul-agent-ca-key.pem -CAcreateserial -out server.<<datacenter>>.consul.crt

# View cert
openssl x509 -text -noout -in server.<<datacenter>>.consul.crt

# Generate cleint certs
openssl req -new -newkey rsa:2048 -nodes -keyout client.<<datacenter>>.consul.key -out client.<<datacenter>>.consul.csr -subj '/CN=client.<<datacenter>>.consul'

# Self sign client cert
openssl x509 -req -in client.<<datacenter>>.consul.csr -CA consul-agent-ca.pem -CAkey consul-agent-ca-key.pem -out client.<<datacenter>>.consul.crt

# Enable SAN
vim server-ext.cnf
echo 'subjectAltName=DNS:server.<<datacenter>>.consul,IP:0.0.0.0' > server-ext.cnf

# Update server cert for x509 extensions
openssl x509 -req -in server.<<datacenter>>.consul.csr -days 60 -CA consul-agent-ca.pem -CAkey consul-agent-ca-key.pem -CAcreateserial -out server.<<datacenter>>.consul.crt -extfile server-ext.cnf

# Update client cert for x509 extensions
openssl x509 -req -in client.<<datacenter>>.consul.csr -days 60 -CA consul-agent-ca.pem -CAkey consul-agent-ca-key.pem -out client.<<datacenter>>.consul.crt -extfile server-ext.cnf
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
    "key_file": "server.<<datacenter>>.consul.key",
    "cert_file": "server.<<datacenter>>.consul.crt",
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