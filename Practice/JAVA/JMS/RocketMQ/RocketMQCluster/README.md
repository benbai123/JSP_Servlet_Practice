
### Tested on Ubuntu 18.04

### Install Docker first

If you haven't you can refer to [Install Docker on Ubuntu 18.04](https://ben-bai.blogspot.com/2019/10/motivation-i-want-to-try-rocketmq.html)

### Download the Dockerfile

Just download or copy/paste source code, both fine.

### Go to the download folder and build an Docker Image

Build an Image as below (in terminal)

`docker build -t rmqi .`

where `-t rmqi` denotes the image name is rmqi

the last `.` is the path to Dockerfile (current folder)

### Run a Docker Container with the built image

You can run a Container with the built image as below

`docker run -d -p 9487:9876 --name rmqc rmqi`

where `-d` means to start a container in detached mode,

`-p 9487:9876` denotes publish port to host interface (Container 9876 to host 9487 here)

`--name rmqc` denotes the Container name is rmqc,

the last `rmqi` is the image name.
