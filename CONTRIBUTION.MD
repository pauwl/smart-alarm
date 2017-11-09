##### Requirements
* Docker Engine
* Docker Compose
* Hypervisor (e.g. Virtualbox, Hyper-V) only for Windows and macOS hosts


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

