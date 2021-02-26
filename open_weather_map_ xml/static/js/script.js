function requisicao(){
    var url_img = 'http://openweathermap.org/img/w/';
    var extensao = '.png';
    var unidade = '&units=metric';
    //var key = '&appid=KEY_API_OPEN_WEATHER_MAP';
    var key = '&appid=8f74f300ec7bb767f83df47b7d85064f&mode=xml';
    var url_maps = 'https://maps.googleapis.com/maps/api/js?';
    var key_maps = 'API_GOOGLE_MAPS';

	var cidade = document.getElementById('cidade').value;
    var req = new XMLHttpRequest();
    
    
    req.onloadend = function(){
        resp_obj = new DOMParser().parseFromString(req.responseText, "text/xml");
        
        error = resp_obj.getElementsByTagName('cod')[0];

        if(error != null && error.innerHTML == '404' ){
            alert('Preenchimento incorreto! Gentileza verificar os dados!');
            return;
        
        }else if(error != null && error.innerHTML == '400'){
            alert('Campo Cidade não pode ficar vazio');
            return
        }else{
    
        var temperatura_xml = resp_obj.getElementsByTagName('temperature')[0];
        var img_xml =resp_obj.getElementsByTagName('weather')[0];
        var sol_xml = resp_obj.getElementsByTagName('sun')[0];
        var coordenada_xml = resp_obj.getElementsByTagName('coord')[0];
        var altura_nivel_mar_xml = resp_obj.getElementsByTagName('pressure')[0];

        titulo.innerHTML = cidade.toUpperCase();
        tempo_atual.innerHTML = temperatura_xml.getAttribute('value') + ' °C';
        tempo_minimo.innerHTML = temperatura_xml.getAttribute('min') + ' °C';
        tempo_maximo.innerHTML = temperatura_xml.getAttribute('max') + ' °C';
        nasc_sol.innerHTML =  converte_hora(sol_xml.getAttribute('rise'));
        por_sol.innerHTML = converte_hora(sol_xml.getAttribute('set'));
        coordenadas.innerHTML = coordenada_xml.getAttribute('lon') + ", " + coordenada_xml.getAttribute('lat') ;
        nivel.innerHTML = altura_nivel_mar_xml.getAttribute('value') + " hpa";
        icone.src =  url_img + img_xml.getAttribute('icon') + extensao;

        maps(coordenada_xml.getAttribute('lat'), coordenada_xml.getAttribute('lon'));
        }
	}

	req.open('GET', 'http://api.openweathermap.org/data/2.5/weather?q=' + cidade + unidade + key);
	req.send(null);

}

function converte_hora(timestamp){
    var date = new Date(timestamp + '+0000');
    return date.getHours() +':'+date.getMinutes();
}

function maps(latitude,longitude){
    var target = document.querySelector('#map');

    var coordinate = new google.maps.LatLng(latitude, longitude);

    var optionsMap = {
                center : coordinate,
                zoom: 19,
                mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    var map = new google.maps.Map(target, optionsMap);

    var configMarker = {
                            position : coordinate,
                            map : map,
                            title: "Você está aqui!"
                        };

    var marker = new google.maps.Marker(configMarker);

  
}
