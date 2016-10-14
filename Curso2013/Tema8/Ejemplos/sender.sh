api_key=AIzaSyC0KejqE2t9yN3d9ko2FY1er1AKeG89Mpo
token=APA91bEj1NGvLc8yvWxNHMVND-UmsdiVwpjwmfckjzVV6UpjZCCSyDgah_BlxvbCpehsHx_mKAnufBRMnJTfqa9GWwJXKoDPWfYA8XZG_wqL0Ni7k2Pw-q977MxK5K49y-4JnrMnYuXIs9EEdKDLL0KOArgzF76m8g
curl --header "Authorization: key=$api_key" --header Content-Type:"application/json" https://android.googleapis.com/gcm/send  -d "{\"data\": {\"score\": \"5x1\",\"time\": \"15:10\"},\"registration_ids\":[\"$token\"]}"
