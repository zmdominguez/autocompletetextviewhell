package com.blogspot.droidista.autocompletetextviewhell;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zarah.dominguez on 11/10/2014.
 */
public class AutoCompleteFragment extends Fragment {

    public AutoCompleteFragment newInstance() {
        AutoCompleteFragment fragment = new AutoCompleteFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Set up our adapter
        CountriesAdapter adapter = new CountriesAdapter(getActivity(), Arrays.asList(COUNTRIES));

        // Set it to the AutoCompleteTextView
        AutoCompleteTextView textView = (AutoCompleteTextView)rootView.findViewById(R.id.countries_autocomplete);
        textView.setAdapter(adapter);


        return rootView;
    }


    private class CountriesAdapter extends BaseAdapter implements Filterable {

        private List<String> mCountries = new ArrayList<String>();
        private List<String> mFilteredData = new ArrayList<String>();
        LayoutInflater mInflater;

        private static final String SECTION_HEADER = "Section!";

        private static final int VIEW_TYPE_HEADER = 0;
        private static final int VIEW_TYPE_ENTRY = 1;
        private static final int VIEW_TYPE_COUNT = 2;

        public CountriesAdapter(Context context, List<String> countries) {
            mCountries = countries;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getViewTypeCount() {
            return VIEW_TYPE_COUNT;
        }

        @Override
        public int getItemViewType(int position) {
            // Remember, any value of view type should return getViewTypeCount()-1!
            return mFilteredData.get(position).equals(SECTION_HEADER) ? VIEW_TYPE_HEADER : VIEW_TYPE_ENTRY;
        }

        @Override
        public int getCount() {
            return mFilteredData.size();
        }

        @Override
        public Object getItem(int i) {
            return mFilteredData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            // Headers should not be clickable
            return !(getItemViewType(position) == VIEW_TYPE_HEADER);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            // Do standard ViewHolder stuff
            ViewHolder holder;

            if(view == null) {
                view = mInflater.inflate(android.R.layout.simple_dropdown_item_1line, viewGroup, false);

                holder = new ViewHolder();
                holder.mText = (TextView) view.findViewById(android.R.id.text1);

                view.setTag(holder);
            } else {
                holder = (ViewHolder)view.getTag();
            }

            // Set the background color
            if(getItemViewType(i) == VIEW_TYPE_HEADER) {
                holder.mText.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            } else {
                holder.mText.setBackgroundColor(getResources().getColor(android.R.color.white));
            }

            // Set the value to be displayed
            holder.mText.setText(mFilteredData.get(i));

            return view;
        }

        @Override
        public Filter getFilter() {
            return mFilter;
        }

        Filter mFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                mFilteredData.clear();

                if(constraint != null) {
                    for (int i = 0; i < mCountries.size(); i++) {
                        // Every fifth item, add a header. Do not care if there
                        // are items after that, we just want to illustrate
                        // a point.
                        if(mFilteredData.size() % 5 == 0) {
                            mFilteredData.add(SECTION_HEADER);
                        }

                        // Filter by start of string
                        String country = mCountries.get(i);
                        if(country.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            mFilteredData.add(country);
                        }
                    }
                }

                Log.d("AutoCompleteFragment", "Filtered results: " + mFilteredData);

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredData;
                filterResults.count = mFilteredData.size();

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                // Tell our adapter about new stuff
                mFilteredData = (List<String>)filterResults.values;
                notifyDataSetChanged();
            }
        };


        class ViewHolder {
            TextView mText;
        }
    }

    private static final String[] COUNTRIES = new String[]{
            "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Anguilla", "Antigua &amp; Barbuda",
            "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain",
            "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan",
            "Bolivia", "Bosnia &amp; Herzegovina", "Botswana", "Brazil", "British Virgin Islands",
            "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Cape Verde",
            "Cayman Islands", "Chad", "Chile", "China", "Colombia", "Congo", "Cook Islands", "Costa Rica",
            "Cote D Ivoire", "Croatia", "Cruise Ship", "Cuba", "Cyprus", "Czech Republic", "Denmark",
            "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador",
            "Equatorial Guinea", "Estonia", "Ethiopia", "Falkland Islands", "Faroe Islands", "Fiji",
            "Finland", "France", "French Polynesia", "French West Indies", "Gabon", "Gambia", "Georgia",
            "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guam", "Guatemala", "Guernsey",
            "Guinea", "Guinea Bissau", "Guyana", "Haiti","Honduras","Hong Kong","Hungary","Iceland",
            "India","Indonesia","Iran","Iraq","Ireland","Isle of Man","Israel","Italy","Jamaica",
            "Japan","Jersey","Jordan","Kazakhstan","Kenya","Kuwait","Kyrgyz Republic","Laos",
            "Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg",
            "Macau","Macedonia","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Mauritania",
            "Mauritius","Mexico","Moldova","Monaco","Mongolia","Montenegro","Montserrat","Morocco",
            "Mozambique","Namibia","Nepal","Netherlands","Netherlands Antilles","New Caledonia",
            "New Zealand","Nicaragua","Niger","Nigeria","Norway","Oman","Pakistan","Palestine",
            "Panama","Papua New Guinea","Paraguay","Peru","Philippines","Poland","Portugal",
            "Puerto Rico","Qatar","Reunion","Romania","Russia","Rwanda","Saint Pierre &amp; Miquelon",
            "Samoa","San Marino","Satellite","Saudi Arabia","Senegal","Serbia","Seychelles",
            "Sierra Leone","Singapore","Slovakia","Slovenia","South Africa","South Korea","Spain",
            "Sri Lanka","St Kitts &amp; Nevis","St Lucia","St Vincent","St. Lucia","Sudan","Suriname",
            "Swaziland","Sweden","Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand",
            "Timor L'Este","Togo","Tonga","Trinidad &amp; Tobago","Tunisia","Turkey","Turkmenistan",
            "Turks &amp; Caicos","Uganda","Ukraine","United Arab Emirates","United Kingdom",
            "Uruguay","Uzbekistan","Venezuela","Vietnam","Virgin Islands (US)","Yemen","Zambia","Zimbabwe"
    };
}
