# GithubAPI.py
# coding: utf-8

# Codifique um programa em linguagem livre que receba como entrada o nome de um usuário do GitHub e,
# utilizando sua API, exiba o nome de usuário e o nome completo (e contagem) de todos os seus seguidores, além do
# nome de todos os repositórios pertencentes a cada um dos seguidores, no formato:
#   Usuário da consulta: jao
#   
#   3 seguidores:
#   
#   Nome_completo_seguidor_1
#       repositorio_que_possui_1
#       repositorio_que_possui_2
#   
#   Nome_completo_seguidor_2
#       repositorio_que_possui_1
#       repositorio_que_possui_2
#       repositorio_que_possui_3
#   
#   Nome_completo_seguidor_1=3
#       repositorio_que_possui_1


import json
import urllib2

urlUsuario = 'https://api.github.com/users/{}'

def main():
    usuario = raw_input("Nome do usuário: ")

    url = urlUsuario.format(usuario)

    retorno = urllib2.urlopen(url).read()

    dados = json.loads(retorno)

    nomeUsuario = dados['name']
    
    if nomeUsuario == None:
        print "\nUsuário da consulta: {}".format(usuario)
    else:
        print "\nUsuário da consulta: {}".format(nomeUsuario)
    
    followers_url = dados['followers_url']

    retorno = urllib2.urlopen(followers_url).read()

    dadosSeguidores = json.loads(retorno)

    followers = []

    for follower in range(0, len(dadosSeguidores)):
        aux = {}

        aux['user'] = dadosSeguidores[follower]['login'].encode('utf-8')
        aux['url'] = dadosSeguidores[follower]['url']
        aux['reps'] = dadosSeguidores[follower]['repos_url']

        followers.append(aux)

    print ("\n{} seguidores: ".format(len(followers)))

    for follower in followers:
        seguidor = follower['user']
        
        url = urlUsuario.format(seguidor)

        retorno = urllib2.urlopen(url).read()

        dados = json.loads(retorno)

        nomeSeguidor = dados['name']
        
        if nomeSeguidor == None:
            print "\n{}".format(seguidor)
        else:
            print "\n{}".format(nomeSeguidor)
    
        dadosRepositorios = urllib2.urlopen(follower['reps']).read()

        repositorios = json.loads(dadosRepositorios)

        for repositorio in repositorios:
            print "\t", repositorio['name']
    

if __name__ == '__main__':
    main()