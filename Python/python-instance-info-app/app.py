from flask import Flask, render_template
import requests
import platform

app = Flask(__name__)

def get_instance_metadata(metadata_key):
    metadata_url = "http://169.254.169.254/latest/meta-data/"
    token = requests.put('http://169.254.169.254/latest/api/token', headers={"X-aws-ec2-metadata-token-ttl-seconds": "300"}).text
    headers = {"X-aws-ec2-metadata-token": token}

    try:
        response = requests.get(metadata_url + metadata_key, headers=headers)
        response.raise_for_status()
        return response.text
    except requests.exceptions.RequestException as e:
        return f"Error retrieving metadata: {e}"

@app.route('/')
def get_instance_info():
    instance_id = get_instance_metadata("instance-id")
    instance_type = get_instance_metadata("instance-type")
    region = get_instance_metadata("placement/availability-zone")[:-1]  # Remove the trailing AZ letter to get the region
    availability_zone = get_instance_metadata("placement/availability-zone")
    cpu_architecture = platform.processor()

    return render_template("index.html",
                           instance_id=instance_id,
                           instance_type=instance_type,
                           region=region,
                           availability_zone=availability_zone,
                           cpu_architecture=cpu_architecture)

@app.route('/cpu-info')
def cpu_info():
    cpu_info_file = '/proc/cpuinfo'
    try:
        with open(cpu_info_file, 'r') as file:
            cpu_info_content = file.read()
    except Exception as e:
        cpu_info_content = f"Error reading {cpu_info_file}: {e}"

    return render_template("cpu_info.html", cpu_info_content=cpu_info_content)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=80)


