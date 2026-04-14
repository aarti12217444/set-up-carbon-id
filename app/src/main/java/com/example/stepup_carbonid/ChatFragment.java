package com.example.stepup_carbonid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatFragment extends Fragment {

    EditText messageBox;
    Button sendBtn;
    ListView chatList;

    ArrayList<String> messages = new ArrayList<>();
    ArrayAdapter<String> adapter;

    OkHttpClient client = new OkHttpClient();

    // 🔐 Put your NEW Gemini API key here
    String API_KEY = "PASTE_YOUR_NEW_API_KEY_HERE";

    public ChatFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        messageBox = v.findViewById(R.id.messageBox);
        sendBtn = v.findViewById(R.id.sendBtn);
        chatList = v.findViewById(R.id.chatList);

        adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                messages
        );

        chatList.setAdapter(adapter);

        sendBtn.setOnClickListener(view -> {

            String msg = messageBox.getText().toString().trim();

            if (msg.isEmpty()) {
                Toast.makeText(getContext(), "Enter message", Toast.LENGTH_SHORT).show();
                return;
            }

            messages.add("You: " + msg);
            adapter.notifyDataSetChanged();
            messageBox.setText("");

            sendToGemini(msg);
        });

        return v;
    }

    private void sendToGemini(String userMessage) {

        try {
            // JSON Body Create
            JSONObject textObj = new JSONObject();
            textObj.put("text", userMessage);

            JSONArray partsArray = new JSONArray();
            partsArray.put(textObj);

            JSONObject contentObj = new JSONObject();
            contentObj.put("parts", partsArray);

            JSONArray contentsArray = new JSONArray();
            contentsArray.put(contentObj);

            JSONObject mainObj = new JSONObject();
            mainObj.put("contents", contentsArray);

            RequestBody body = RequestBody.create(
                    mainObj.toString(),
                    MediaType.get("application/json")
            );

            Request request = new Request.Builder()
                    .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    requireActivity().runOnUiThread(() -> {
                        messages.add("Bot Error: " + e.getMessage());
                        adapter.notifyDataSetChanged();
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String res = response.body().string();

                    requireActivity().runOnUiThread(() -> {
                        try {
                            JSONObject jsonObject = new JSONObject(res);

                            // Show HTTP Code for Debug
                            messages.add("Code: " + response.code());

                            if (jsonObject.has("error")) {

                                String errorMsg = jsonObject
                                        .getJSONObject("error")
                                        .getString("message");

                                messages.add("Bot Error: " + errorMsg);

                            } else {

                                JSONArray candidates = jsonObject.getJSONArray("candidates");

                                JSONObject content = candidates
                                        .getJSONObject(0)
                                        .getJSONObject("content");

                                JSONArray parts = content.getJSONArray("parts");

                                String reply = parts
                                        .getJSONObject(0)
                                        .getString("text");

                                messages.add("Bot: " + reply);
                            }

                        } catch (Exception e) {
                            messages.add("Bot: Parsing Failed");
                            messages.add(res);
                        }

                        adapter.notifyDataSetChanged();
                    });
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(getContext(),
                    "Something went wrong",
                    Toast.LENGTH_SHORT).show();
        }
    }
}