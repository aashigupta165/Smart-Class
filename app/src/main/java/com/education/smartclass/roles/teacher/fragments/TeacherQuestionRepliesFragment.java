package com.education.smartclass.roles.teacher.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.smartclass.Adapter.ReplyListAdapter;
import com.education.smartclass.R;
import com.education.smartclass.holder.ReplyListHolder;
import com.education.smartclass.models.Reply;
import com.education.smartclass.roles.teacher.model.DeleteQuestionViewModel;
import com.education.smartclass.roles.teacher.model.DeleteReplyViewModel;
import com.education.smartclass.roles.teacher.model.FetchReplyOfQuestionViewModel;
import com.education.smartclass.roles.teacher.model.PostReplyViewModel;
import com.education.smartclass.storage.SharedPrefManager;
import com.education.smartclass.utils.SnackBar;

import java.util.ArrayList;

public class TeacherQuestionRepliesFragment extends Fragment {

    private TextView askername, question, askerstd, questiontime, no_data, sendbtn;
    private EditText reply_text;
    private ImageView deletebtn;

    private RelativeLayout relativeLayout;

    private RecyclerView reply_list;

    private ReplyListAdapter replyListAdapter;

    private ProgressDialog progressDialog;

    private ProgressBar progressBar;

    private ArrayList<Reply> replyArrayList;

    private int positionDelete;

    private String id, name, ques, std, sec, time, role;

    private PostReplyViewModel postReplyViewModel;
    private FetchReplyOfQuestionViewModel fetchReplyOfQuestionViewModel;
    private DeleteQuestionViewModel deleteQuestionViewModel;
    private DeleteReplyViewModel deleteReplyViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                getParentFragmentManager().popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_question_replies, container, false);

        askername = view.findViewById(R.id.name);
        deletebtn = view.findViewById(R.id.delete);
        question = view.findViewById(R.id.question);
        askerstd = view.findViewById(R.id.std);
        questiontime = view.findViewById(R.id.time);
        reply_text = view.findViewById(R.id.reply_text);
        reply_list = view.findViewById(R.id.replies_list);
        sendbtn = view.findViewById(R.id.post_reply);
        no_data = view.findViewById(R.id.no_replies);

        if (SharedPrefManager.getInstance(getContext()).getUser().getRole().equals("Student")){
            deletebtn.setVisibility(View.GONE);
        }

        relativeLayout = view.findViewById(R.id.relativeLayout);

        progressDialog = new ProgressDialog(getContext());

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        Bundle bundle = this.getArguments();

        id = bundle.getString("questionId");
        ques = bundle.getString("question");
        name = bundle.getString("askerName");
        role = bundle.getString("askerRole");
        std = bundle.getString("askerClass");
        sec = bundle.getString("askerSection");
        time = bundle.getString("date");

        askername.setText("Posted by: " + name);
        question.setText(ques);
        questiontime.setText(time);

        if (role.equals("Teacher")) {
            askerstd.setText("Faculty");
        } else {
            askerstd.setText("STD: " + std + " " + sec);
        }

        dataObserver();
        buttonClickEvents();

        fetchReplyOfQuestionViewModel.fetchReplies(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), id);

        return view;
    }

    private void buttonClickEvents() {

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteQuestion();
            }
        });

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postReplyToQuestion();
                sendbtn.setEnabled(false);
            }
        });
    }

    private void deleteQuestion() {

        AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.setMessage("deleting...");
                        progressDialog.show();
                        deleteQuestionViewModel.delete(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), id);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void dataObserver() {

        postReplyViewModel = ViewModelProviders.of(this).get(PostReplyViewModel.class);
        LiveData<String> message = postReplyViewModel.getMessage();

        message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                sendbtn.setEnabled(true);
                switch (s) {
                    case "replied":
                        reply_text.setText("");
                        fetchReplyOfQuestionViewModel.fetchReplies(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), id);
                        break;
                    case "invalid_orgCode":
                        new SnackBar(relativeLayout, "Invalid Details");
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    default:
                        new SnackBar(relativeLayout, "Please Try Again Later!");
                }
            }
        });

        fetchReplyOfQuestionViewModel = ViewModelProviders.of(this).get(FetchReplyOfQuestionViewModel.class);
        LiveData<String> msg = fetchReplyOfQuestionViewModel.getMessage();

        msg.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressBar.setVisibility(View.GONE);
                switch (s) {
                    case "list_found":
                        setRepliesList();
                        break;
                    case "invalid_orgCode":
                        new SnackBar(relativeLayout, "Invalid Details");
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    default:
                        new SnackBar(relativeLayout, "Please Try Again Later!");
                }
            }
        });

        deleteQuestionViewModel = ViewModelProviders.of(this).get(DeleteQuestionViewModel.class);
        LiveData<String> messages = deleteQuestionViewModel.getMessage();

        messages.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                switch (s) {
                    case "question_deleted":
                        TeacherQuestionaireFragment fragment = new TeacherQuestionaireFragment();
                        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                        break;
                    case "invalid_orgCode":
                        new SnackBar(relativeLayout, "Invalid Details");
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    default:
                        new SnackBar(relativeLayout, "Please Try Again Later!");
                }
            }
        });

        deleteReplyViewModel = ViewModelProviders.of(this).get(DeleteReplyViewModel.class);
        LiveData<String> msgs = deleteReplyViewModel.getMessage();

        msgs.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                switch (s) {
                    case "reply_deleted":
                        replyArrayList.remove(positionDelete);
                        replyListAdapter.notifyItemRemoved(positionDelete);
                        break;
                    case "invalid_orgCode":
                        new SnackBar(relativeLayout, "Invalid Details");
                        break;
                    case "Internet_Issue":
                        new SnackBar(relativeLayout, "Please connect to the Internet!");
                        break;
                    default:
                        new SnackBar(relativeLayout, "Please Try Again Later!");
                }
            }
        });
    }

    private void setRepliesList() {

        LiveData<ArrayList<Reply>> list = fetchReplyOfQuestionViewModel.getList();

        list.observe(this, new Observer<ArrayList<Reply>>() {
            @Override
            public void onChanged(ArrayList<Reply> replies) {

                replyArrayList = replies;

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                reply_list.setLayoutManager(linearLayoutManager);
                replyListAdapter = new ReplyListAdapter(getContext(), replies);
                reply_list.setAdapter(replyListAdapter);

                replyListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        if (replyListAdapter.getItemCount() == 0) {
                            no_data.setVisibility(View.VISIBLE);
                        }
                    }
                });

                replyListAdapter.setOnItemClickListener(new ReplyListHolder.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        delete(position);
                    }
                });
            }
        });
    }

    private void delete(int position) {

        AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItem(position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteItem(int position) {

        progressDialog.setMessage("Deleting...");
        progressDialog.show();

        String orgCode = SharedPrefManager.getInstance(getContext()).getUser().getOrgCode();

        positionDelete = position;

        deleteReplyViewModel.delete(orgCode, id, replyArrayList.get(position).getReplyId());
    }


    private void postReplyToQuestion() {

        if (reply_text.getText().toString().equals("")) {
            new SnackBar(relativeLayout, "Please give reply!");
            return;
        }

        if (SharedPrefManager.getInstance(getContext()).getUser().getRole().equals("Teacher")) {
            postReplyViewModel.postReply(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), id, reply_text.getText().toString(),
                    SharedPrefManager.getInstance(getContext()).getUser().getTeacherName(), SharedPrefManager.getInstance(getContext()).getUser().getRole(),
                    SharedPrefManager.getInstance(getContext()).getUser().getTeacherCode(), null, null);
        } else {
            postReplyViewModel.postReply(SharedPrefManager.getInstance(getContext()).getUser().getOrgCode(), id, reply_text.getText().toString(),
                    SharedPrefManager.getInstance(getContext()).getUser().getStudentName(), SharedPrefManager.getInstance(getContext()).getUser().getRole(),
                    SharedPrefManager.getInstance(getContext()).getUser().getStudentRollNo(), SharedPrefManager.getInstance(getContext()).getUser().getStudentClass(),
                    SharedPrefManager.getInstance(getContext()).getUser().getStudentSection());
        }
    }
}