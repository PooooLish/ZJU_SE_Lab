from flask import Flask, request, jsonify
from openai import OpenAI
import httpx
from flask_cors import CORS

app = Flask(__name__)
CORS(app, origins="*", supports_credentials=True)

client = OpenAI(
    base_url="https://api.xty.app/v1",
    api_key="sk-veAgjs8Xs4IdERbrE8FdD86a60C44d9296F6A5EaCa1d2b69", #这里要填一个token，可能需要去淘宝买
    http_client=httpx.Client(
        base_url="https://api.xty.app/v1",
        follow_redirects=True,
    ),
)

@app.route('/api/completions', methods=['POST'])
def get_completion():
    data = request.get_json()
    message = data.get('gptai', [])
    print(message)
    messages = [{"role": "user", "content": message}]

    if not messages:
        return jsonify({'error': 'messages is required'}), 400

    completion = client.chat.completions.create(
        model="gpt-3.5-turbo",
        messages=messages
        # stream=True
    )

    # Convert the completion object to a dictionary
    completion_dict = completion.dict()

    # Extract the content of the message
    message_content = completion_dict['choices'][0]['message']['content']

    return jsonify({'message': message_content})

if __name__ == '__main__':
    app.run(port=3000)