Configure docker machine

docker-machine create --driver virtualbox \
    --virtualbox-cpu-count "4" \
    --virtualbox-memory "4096" \
    smart-alarm-machine
    
   
To work with created docker machine execute  

`docker-machine env smart-alarm-machine`  
`eval $(docker-machine env smart-alarm-machine)`