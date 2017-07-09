package me.chenyongrui.movism.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chenyongrui.movism.AppComponent;
import me.chenyongrui.movism.R;

public class LicenseActivity extends BaseActivity {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void injectDependencies(AppComponent appComponent) {
    }


    private List loadDataFromXML(int xmlFileID) {
        List<LicenseListAdapter.LicenseItem> licenseItemList = new ArrayList();
        try {
            InputStream is = getResources().openRawResource(xmlFileID);
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
            Element element = doc.getDocumentElement();
            element.normalize();
            NodeList nList = doc.getElementsByTagName("license");
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element ele = (Element) node;
                    String author = ele.getElementsByTagName("license_author").item(0).getChildNodes().item(0).getNodeValue();
                    String intro = ele.getElementsByTagName("license_intro").item(0).getChildNodes().item(0).getNodeValue();
                    String name = ele.getElementsByTagName("license_name").item(0).getChildNodes().item(0).getNodeValue();
                    licenseItemList.add(new LicenseListAdapter.LicenseItem(author, intro, name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return licenseItemList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        ButterKnife.bind(this);

        SetupToolBar(toolbar, "License", null, true);

        setRecycleAdapter();

    }

    private void setRecycleAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);
        LicenseListAdapter licenseListAdapter = new LicenseListAdapter(loadDataFromXML(R.raw.license));
        recycler.setAdapter(licenseListAdapter);
    }


    static class LicenseListAdapter extends RecyclerView.Adapter<LicenseListAdapter.ViewHolder> {
        private List<LicenseItem> licenseItemList;

        public LicenseListAdapter(List<LicenseItem> licenseItemList) {
            this.licenseItemList = licenseItemList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_license, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final LicenseItem item = licenseItemList.get(position);
            holder.libLicenseAuthor.setText(item.libLicenseAuthor);
            holder.libLicenseName.setText(item.libLicenseName);
            holder.libLicenseIntro.setText(item.libLicenseIntro);
        }


        @Override
        public int getItemCount() {
            return licenseItemList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.lib_license_name)
            TextView libLicenseName;
            @BindView(R.id.lib_license_author)
            TextView libLicenseAuthor;
            @BindView(R.id.lib_license_intro)
            TextView libLicenseIntro;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

        public static class LicenseItem {
            String libLicenseAuthor;
            String libLicenseIntro;
            String libLicenseName;

            public LicenseItem(String libLicenseAuthor, String libLicenseIntro, String libLicenseName) {
                this.libLicenseAuthor = libLicenseAuthor;
                this.libLicenseIntro = libLicenseIntro;
                this.libLicenseName = libLicenseName;
            }
        }
    }
}
