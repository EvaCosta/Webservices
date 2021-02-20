function requisicao(){
    var url_img = 'http://openweathermap.org/img/w/';
    var extensao = '.png';
    var unidade = '&units=metric';
    var key = '&appid=KEY_API_OPEN_WEATHER_MAP';
    var url_maps = 'https://maps.googleapis.com/maps/api/js?';
    var key_maps = 'API_GOOGLE_MAPS';

	var cidade = document.getElementById('cidade').value;
    //alert(cidade);
    var req = new XMLHttpRequest();
    
    
    req.onloadend = function(){
		resp = req.responseText;
        
        resp_obj = JSON.parse(resp);

        //alert(resp_obj.cod)

        if(resp_obj.cod == 200){

        
            var temp_atual = resp_obj.main.temp;
            var temp_min = resp_obj.main.temp_min;
            var temp_max = resp_obj.main.temp_max;
            var img = url_img + resp_obj.weather[0].icon + extensao;
            var nascimento_sol = converte_hora(resp_obj.sys.sunrise);
            var por_do_sol = converte_hora(resp_obj.sys.sunset);
            var longitude = resp_obj.coord.lon;
            var latitude = resp_obj.coord.lat;
            var altura_nivel_mar = resp_obj.main.sea_level;

            titulo.innerHTML = cidade.toUpperCase();
            tempo_atual.innerHTML = temp_atual + ' °C';
            tempo_minimo.innerHTML = temp_min + ' °C';
            tempo_maximo.innerHTML = temp_max + ' °C';
            nasc_sol.innerHTML = nascimento_sol;
            por_sol.innerHTML = por_do_sol;
            coordenadas.innerHTML = longitude + ", " + latitude;
            nivel.innerHTML = altura_nivel_mar + " hpa";
            icone.src = img;

            maps(latitude, longitude);
        }else if(resp_obj.cod == 404){
            alert('Preenchimento incorreto! Gentileza verificar os dados!');
            return;
        }else if(resp_obj.cod == 400){
            alert('Campo Cidade não pode ficar vazio');
            return
        }
	}

	req.open('GET', 'http://api.openweathermap.org/data/2.5/weather?q=' + cidade + unidade + key);
	req.send(null);

}

function converte_hora(timestamp){
    var date = new Date(timestamp * 1000);
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
