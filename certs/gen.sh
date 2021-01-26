rm *.pem

# 1. Generate CA's private key and self-signed certificate
openssl req -x509 -newkey rsa:4096 -days 365 -nodes -keyout ca-key.pem -out ca-cert.pem -subj "/C=US/ST=California/L=Pasadena/O=AMG/OU=Dev/CN=server.amg.consul/emailAddress=iamatulmgupta@gmail.com"

echo "CA's self-signed certificate"
openssl x509 -in ca-cert.pem -noout -text

# 2. Generate web server's private key and certificate signing request (CSR)
openssl req -newkey rsa:4096 -nodes -keyout server-key.pem -out server-req.pem -subj "/C=US/ST=California/L=Pasadena/O=AMG/OU=Dev/CN=server.amg.consul/emailAddress=iamatulmgupta@gmail.com"

# 3. Use CA's private key to sign web server's CSR and get back the signed certificate
openssl x509 -req -in server-req.pem -days 60 -CA ca-cert.pem -CAkey ca-key.pem -CAcreateserial -out server-cert.pem -extfile server-ext.cnf

echo "Server's signed certificate"
openssl x509 -in server-cert.pem -noout -text


openssl req -x509 -newkey rsa:4096 -days 365 -keyout ca-key.pem -out ca-cert.pem -subj "/C=FR/ST=Occitanie/L=Toulouse/O=Tech School/OU=Education/CN=*.techschool.guru/emailAddress=techschool.guru@gmail.com"



subjectAltName=DNS:*server.amg.consul,DNS:*.server.amg.consul,IP:0.0.0.0


openssl req -new -newkey rsa:2048 -nodes -keyout cli.client.dc1.consul.key -out cli.client.dc1.consul.csr -subj '/CN=cli.client.dc1.consul'

openssl x509 -req -in server-req.pem -days 60 -CA ca-cert.pem -CAkey ca-key.pem -CAcreateserial -out server-cert.pem -extfile server-ext.cnf


curl https://consul.com:8501/ui/ \
  --resolve 'consul.com:8501:127.0.0.1' \
  --cacert consul-agent-ca.pem