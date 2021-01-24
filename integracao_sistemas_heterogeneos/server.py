# server.py
# coding: utf-8

''' Para cada turma, exibirá a seguinte mensagem na tela:
 A turma nome_turma de ano_turma do curso nome_curso possui qtd_alunos_turma alunos, dos quais
 qtd_alunos_matriculados_turma estão devidamente matriculados.'''

import socket
import json


HOST = '127.0.0.1'
PORT = 16200 
serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
serverSocket.bind((HOST, PORT))
serverSocket.listen(1)

print("The server is ready to receive")

while True:
    connectionSocket, addr = serverSocket.accept()
    print("Conexão vinda de {}".format(addr))

    turmas = connectionSocket.recv(2048).decode('utf-8')

    turma = json.loads(turmas)

    qtd_turmas = len (turma)

    for contador in range(0, qtd_turmas):
        nome_turma = turma[contador]['nome'] 
        ano_turma = turma[contador]['ano'] 
        nome_curso = turma[contador]['curso']
        qtd_alunos_turma = len( turma[contador]['alunos'] )

        qtd_alunos_matriculados_turma = 0

        for posicao in range(0, qtd_alunos_turma):
            aluno = turma[contador]['alunos'][posicao] 

            if aluno['matriculado'] == True:
                qtd_alunos_matriculados_turma += 1;

        print('\n\tA turma \"{}\" de {} do curso \"{}\" \n\tpossui {} aluno(s), dos quais {} estao devidamente matriculados.\n'.format(nome_turma, ano_turma, nome_curso, qtd_alunos_turma, qtd_alunos_matriculados_turma))

        contador += 1
    
    connectionSocket.close()