# coding: utf-8
# GithubAPI.py

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

def requisicao(url):
    retorno = urllib2.urlopen(url).read()

    return json.loads(retorno)

def main():
    usuario = raw_input("Nome do usuário: ")

    try:
        dados = requisicao(urlUsuario.format(usuario))
   
        nomeUsuario = dados['name']
    
        if nomeUsuario == None:
            print "\nUsuário da consulta: {}".format(usuario)
        else:
            print "\nUsuário da consulta: {}".format(nomeUsuario)

        obterSeguidores(dados)
    except Exception:
        print "Usuário inexistente"

def obterSeguidores(dados):
    dadosSeguidores = requisicao(dados['followers_url'])

    seguidores = []

    for follower in range(0, len(dadosSeguidores)):
        auxiliar = {}

        auxiliar['usuario'] = dadosSeguidores[follower]['login'].encode('utf-8')
        auxiliar['url'] = dadosSeguidores[follower]['url']
        auxiliar['repositorio'] = dadosSeguidores[follower]['repos_url']

        seguidores.append(auxiliar)

    print ("\n{} seguidores: ".format(len(seguidores)))

    obterRepositorios(seguidores)

def obterRepositorios(seguidores):
    for seguidor in seguidores:
        nomeSeguidor = seguidor['usuario']

        dadosSeguidor = requisicao(urlUsuario.format(nomeSeguidor))

        nomeCompletoSeguidor = dadosSeguidor['name'].encode('utf-8')
        
        if nomeCompletoSeguidor == None:
            print "\n{}".format(nomeSeguidor)
        else:
            print "\n{}".format(nomeCompletoSeguidor)

        exibirRepositorios(seguidor)

def exibirRepositorios(seguidor):
    repositorios = requisicao(seguidor['repositorio'])

    for repositorio in repositorios:
        print "\t{}".format(repositorio['name'])
    

if __name__ == '__main__':
    main()