package com.walk.angel.angelwalk.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.walk.angel.angelwalk.Activity.WheelchairChargingActivity;
import com.walk.angel.angelwalk.Data.SightList;
import com.walk.angel.angelwalk.Data.SightsData;
import com.walk.angel.angelwalk.Data.WheelChairData;
import com.walk.angel.angelwalk.DaumMapSchemeURL;
import com.walk.angel.angelwalk.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MapFragment extends Fragment {


    MapView mapView;
    ViewGroup MapFragment;

    private double currentLongitude;
    private double currentLatitude;
    private SharedPreferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MapFragment = (ViewGroup) inflater.inflate(R.layout.fragment_map, container, false);

        return MapFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        pref = view.getContext().getSharedPreferences("currentPosition", MODE_PRIVATE);
        requestPermission();
        createMapView();
    }

    @Override
    public void onPause() {
        super.onPause();

        //MapFragment.removeView(mapView);
    }

    public void requestPermission() {
        // 권한 물어서 권한안되어있으면 권한 셋팅해주기
        int permissionCheck1 = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.INTERNET);
        if(permissionCheck1 == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.INTERNET},1);

        int permissionCheck2 = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        if(permissionCheck2 == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},1);

        int permissionCheck3 = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck3 == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);


        // LocationManager 객체를 얻어온다
        final LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        // GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록
        try {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                    100, // 통지사이의 최소 시간간격 (miliSecond)
                    1, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                    100, // 통지사이의 최소 시간간격 (miliSecond)
                    1, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);
        } catch(SecurityException e){
            e.printStackTrace();
        }
    }

    public void createMapView(){
        //다음이 제공하는 MapView객체 생성 및 API Key 설정
        mapView = new MapView(this.getActivity());
        mapView.setDaumMapApiKey("edf139f45cfd471ad648c79df3433d57");

        //xml에 선언된 map_view 레이아웃을 찾아온 후, 생성한 MapView객체 추가
        RelativeLayout mapContainer = (RelativeLayout) getView().findViewById(R.id.map_view);
        mapContainer.addView(mapView);


        // 줌 인
        mapView.zoomIn(true);
        // 줌 아웃
        mapView.zoomOut(true);

        try {
            SightList sight = new SightList();
            sight.setArrayListOfSightData();
            ArrayList<SightsData> arrayListOfSight  = sight.getArrayListOfSightData();
            for(int index = 0; index < arrayListOfSight.size(); index++){
                MapPOIItem sightMarker = new MapPOIItem();
                sightMarker.setItemName(arrayListOfSight.get(index).getName());
                sightMarker.setTag(0);
                sightMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.valueOf(arrayListOfSight.get(index).getLatitude()),Double.valueOf(arrayListOfSight.get(index).getLongitude())));
                sightMarker.setMarkerType(MapPOIItem.MarkerType.YellowPin); //
                sightMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

                mapView.addPOIItem(sightMarker);
            }
        }catch (Exception e){
            Log.d("error", "Create Sights Marker Error is " +e.toString());
        }

        try {
            WheelChairData wheelChairData = new WheelChairData();
            wheelChairData.setChargingLocation(getActivity().getAssets());
            wheelChairData.setLocationInfo();
            ArrayList<String> arrayListOfLocationName = wheelChairData.getLocationNames();
            ArrayList<String> arrayListOfLongitude = wheelChairData.getLocationLongitudes();
            ArrayList<String> arrayListOfLatitude = wheelChairData.getLocationLatitudes();

            for(int index = 0; index < arrayListOfLocationName.size(); index++){
                MapPOIItem wheelChairMarker = new MapPOIItem();
                wheelChairMarker.setItemName(arrayListOfLocationName.get(index));
                wheelChairMarker.setTag(0);
                wheelChairMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.valueOf(arrayListOfLatitude.get(index)),Double.valueOf(arrayListOfLongitude.get(index))));
                wheelChairMarker.setMarkerType(MapPOIItem.MarkerType.BluePin); //
                wheelChairMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

                mapView.addPOIItem(wheelChairMarker);
            }
        }catch (Exception e){
            Log.d("error", "Create WheelChair Marker Error is " +e.toString());
        }
    }

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.

            currentLongitude = location.getLongitude(); //경도
            currentLatitude = location.getLatitude();   //위도

            //SharedPreferences pref = getActivity().getSharedPreferences("currentPosition", MODE_PRIVATE);
            if (pref != null) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("currentLongitude", String.valueOf(currentLongitude));
                editor.putString("currentLatitude", String.valueOf(currentLatitude));
                editor.commit();
            }

            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
            mapView.setShowCurrentLocationMarker(true); // 현위치 표시

            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(currentLatitude, currentLongitude), true);

            mapView.setPOIItemEventListener(poiItemEventListener);
        }
        public void onProviderDisabled(String provider) {
            // Disabled시
        }

        public void onProviderEnabled(String provider) {
            // Enabled시

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
        }
    };

    private MapView.POIItemEventListener poiItemEventListener = new MapView.POIItemEventListener() {
        @Override
        public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
            MapPoint.GeoCoordinate geoCoord = mapPOIItem.getMapPoint().getMapPointGeoCoord();
            double latitude = geoCoord.latitude;
            double longitude = geoCoord.longitude;

            Intent intent = getDaumIntent(latitude, longitude);
            if(intent != null){
                startActivity(intent);
            }
        }

        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

        }

        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

        }

        @Override
        public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

        }
    };

    private Intent getDaumIntent(double latitude, double longitude){
        String url = "daummaps://route?sp="+currentLatitude+","+currentLongitude+"&ep="+latitude+","+longitude+"&by=PUBLICTRANSIT";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        DaumMapSchemeURL daumMapSchemeURL = new DaumMapSchemeURL(getContext(), intent) {
            @Override
            public boolean canOpenDaummapURL() {
                return super.canOpenDaummapURL();
            }
        };

        if(daumMapSchemeURL.existDaummapApp()){
            return intent;
        } else {
            DaumMapSchemeURL.openDaummapDownloadPage(getContext());
        }
        return null;
    }
}


/**

 위치정보 미수신중일 때 자원해제해야함

 lm.removeUpdates(mLocationListener);

 */