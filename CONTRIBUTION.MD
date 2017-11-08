##### Requirements
* Docker Engine
* Docker Compose
* Docker Machine and hypervisor (e.g. Virtualbox, Hyper-V) only for Windows and macOS hosts

##### Creation of Docker Machine is required only for Windows and macOS hosts
Configure docker machine

```
docker-machine create --driver virtualbox \
    --virtualbox-cpu-count "2" \
    --virtualbox-memory "4096" \
    smart-alarm-machine
``` 
   
To work with created docker machine execute  

`docker-machine env smart-alarm-machine`  
`eval $(docker-machine env smart-alarm-machine)`


Available, pre-configured environments:
* `production`
* `dev`

##### Building image
(Re)building an image is required whenever:  
* Dockerfile of any service has been modified
* new requirements has been added to requirements-<env_name>.txt file(s)   
located in `smart_alarm/requirements`
```
docker-compose -f docker-compose-<env_name>.yml build
```

##### Run app
```
docker-compose -f docker-compose-<env_name>.yml up
```

