/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var urlg = "https://maps.googleapis.com/maps/api/js"; 


function atualizacep(cep) {
    //alert('entrou aqui');
    cep = cep.replace(/\D/g, "");
    url = "http://cep.correiocontrol.com.br/" + cep + ".js";
    s = document.createElement('script');
    s.setAttribute('charset', 'utf-8');
    s.src = url;
    document.querySelector('head').appendChild(s);
}

function correiocontrolcep(valor) {
    if (valor.erro) {
        alert('Cep não encontrado');
        return;
    }
    
    document.getElementById('logradouro').value = valor.logradouro;
    document.getElementById('bairro').value = valor.bairro;
    document.getElementById('cidade').value = valor.localidade;
    document.getElementById('uf').value = valor.uf;
    alert(valor.bairro);
}
