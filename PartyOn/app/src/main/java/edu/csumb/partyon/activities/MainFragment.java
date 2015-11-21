package edu.csumb.partyon.fragments;

// Add this to the header of your file:
import com.facebook.FacebookSdk;

/**
 * Created by Tobias on 20.11.2015.
 */
public class MainFragment extends Fragment {

    public MainFragment() {

    }

    // Updated your class body:
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        // Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view savedInstanceState);
        LoginButton loginButton = (loginButton) view.findViewById(R.id.login_button);
    }
}
