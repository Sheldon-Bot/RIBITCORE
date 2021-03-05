# This program takes in 3 inputs.
# Example: `python camera.py url camera_name`
import sys
import requests
import cv2

print("ready") # This is necessary so the Java watchdog knows that the script has began.

args = sys.argv[1:]
url = args[0]

cap = cv2.VideoCapture(args[1])

framecount = 0

while True:
    framecount += 1

    print(framecount)

    ret, frame = cap.read()

    encode_param = [int(cv2.IMWRITE_JPEG_QUALITY), 10]
    is_success, im_buf_arr = cv2.imencode('.jpeg', frame, encode_param)

    if not is_success:
        continue

    files = {'image': im_buf_arr}

    r = requests.post(url, files=files)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()