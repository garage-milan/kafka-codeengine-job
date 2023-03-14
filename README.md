# IBM Cloud Code Engine Job - IBM Event Streams Consumer

# TLDR;

## Prerequisite
- [ ] Create an Event Streams instance with a topic named `my-topic`
- [ ] Create a Code Engine project
- [ ] Create a registry secret name `ce-auto-icr-private-global` for `private.icr.io` registry

## RUN LOCAL
```sh
export MESSAGEHUB_BOOTSTRAP_ENDPOINTS=<bootstrap-servers>
export MESSAGEHUB_PASSWORD=<api-key>
git clone https://github.com/garage-milan/kafka-codeengine-job.git
cd kafka-codeengine-job
./mvnw spring-boot:run
```

## DOCKER BUILD
```sh
docker build -t  kafka-ce-job:latest .
docker tag icr.io/<cr_namespace>/kafka-ce-job:latest  
ibmcloud login --sso
ibmcloud cr region-set global
ibmcloud cr login
docker push icr.io/<cr_namespace>/kafka-ce-job:latest
```


## IBM CODE ENGINE JOB
```sh
ibmcloud ce project select -n <code_engine_project>
ibmcloud ce job create --name kafka-codeengine-job --image "private.icr.io/<cr-namespace>/kafka-ce-job"  \
  --cpu "0.125" --memory "0.25G" --mode "daemon" \
  --registry-secret ce-auto-icr-private-global
ibmcloud ce job bind --name kafka-codeengine-job --service-instance <event_streams_instance_name> --role Reader
ibmcloud ce jobrun submit --job kafka-codeengine-job
JOB=$(ibmcloud ce jobrun submit --job kafka-codeengine-job --output json | jq -r '.metadata.name')
ibmcloud ce jr logs --name $JOB
```