# python-instance-info-app

## Pre-requsite
1. Python3
2. pip3
3. docker

## Instruction to use

1. Clone the repo
```
git clone https://github.com/santosh-at-github/instance-info-apps.git
```

2. Change directory to app base directory.
```
cd ./instance-info-apps/Python/python-instance-info-app/
```

3. Install required dependency.
```
pip3 install -r requirements.txt
```

4. Run the application
```
python3 app.py
```
5. To containerized the app, execute below command
```
docker build -t python-instance-info-app:latest .
```

6. Execute below command to run containerized application.
```
docker run -p 8080:80 python-instance-info-app:latest
```
