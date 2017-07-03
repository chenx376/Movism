package me.chenyongrui.movism.mvp.activities;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

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
import me.chenyongrui.movism.R;
import me.chenyongrui.movism.di.component.AppComponent;
import me.chenyongrui.movism.mvp.adapters.LicenseListAdapter;

public class LicenseActivity extends BaseActivity {
    @BindView(R.id.license_recycler)
    RecyclerView licenseRecycler;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void injectDependencies(AppComponent appComponent) {
        appComponent.inject(this);
    }

    private String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
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
                    Element element2 = (Element) node;
                    licenseItemList.add(
                            new LicenseListAdapter.LicenseItem(getValue("license_author", element2)
                            , getValue("license_intro", element2)
                            , getValue("license_name", element2)));
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

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("License");
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        licenseRecycler.setLayoutManager(linearLayoutManager);
        licenseRecycler.setAdapter(new LicenseListAdapter(this, loadDataFromXML(R.raw.license)));
    }

}
