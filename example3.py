import harmat as hm
import socket


#client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

#client_socket.connect(("localhost", 5000))

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind(("localhost", 5000))
s.listen(1)
end = "c"
conn, addr = s.accept()

datas=[];
while 1:
    data = conn.recv(1024)
    conn.sendall(data)
    datas.append(data.decode('utf-8'))
    if end in data.decode('utf-8'):
        break    

#print(data.decode('utf-8'))

#dData = data.decode('utf-8').rstrip('\n')

print(datas)


numberOfNodes = int(datas[0])



#numberOfArcs = int(dData[2])
#print(numberOfArcs)
#for x in range (4, 4+numberOfArcs*2, 3):
#    print(dData[x])
#    print(dData[x+1])



# initialise the harm
h = hm.Harm()

# create the top layer of the harm
# top_layer refers to the top layer of the harm
h.top_layer = hm.AttackGraph()

# we will create 5 nodes and connect them in some way
# first we create some nodes
hosts = [hm.Host("Host {}".format(i)) for i in range(numberOfNodes)]
# then we will make a basic attack tree for each
for host in hosts:
    host.lower_layer = hm.AttackTree()
    # We will make two vulnerabilities and give some metrics
    vulnerability1 = hm.Vulnerability('CVE-0000', values = {
        'risk' : 9,
        'cost' : 4,
        'probability' : 0.2,
        'impact' : 12
    })
    vulnerability2 = hm.Vulnerability('CVE-0001', values = {
        'risk' : 1,
        'cost' : 5,
        'probability' : 0.2,
        'impact' : 2
    })
    # basic_at creates just one OR gate and puts all vulnerabilites
    # the children nodes
    host.lower_layer.basic_at([vulnerability1, vulnerability2])
    


hosts[int(datas[1])] = hm.Attacker()



# Now we will create an Attacker. This is not a physical node but it exists to describe
# the potential entry points of attackers.
#attacker = hm.Attacker() 


#for x in range (4, 4+numberOfArcs*2, 3):
#    print(int(dData[x])+int(dData[x+1]))
#    print(int(dData[x]))
#    print(int(dData[x+1]))
#    h[0].add_edge(hosts[int(dData[x])], hosts[int(dData[x+1])]) 


#f2 = open("NodeInput.txt", "r")
#for x in f2:
#    print(x)

for x in range (3, len(datas)-1, 2):
    print(int(datas[x]))
    print(int(datas[x+1]))
    h[0].add_edge(hosts[int(datas[x])], hosts[int(datas[x+1])])
# To add edges we simply use the add_edge function
# here h[0] refers to the top layer
# add_edge(A,B) creates a uni-directional from A -> B.
# h[0].add_edge(attacker, hosts[0]) 
# h[0].add_edge(hosts[0], hosts[3])
# h[0].add_edge(hosts[1], hosts[0])
# h[0].add_edge(hosts[0], hosts[2])
# h[0].add_edge(hosts[3], hosts[2])

# Now we set the attacker and target
h[0].source = hosts[int(datas[1])]
h[0].target = hosts[int(datas[2])]

# do some flow up
h.flowup()

# Now we will run some metrics
hm.HarmSummary(h).show()


#client_socket.send(("Number of hosts: " + str(numberOfNodes) + "\n").encode('utf-8'))

#client_socket.send(cedit)

a = "Number of hosts: " + str(numberOfNodes) + "\n"
abytes = a.encode('utf-8')
conn.send(abytes)

risk = "Risk: " + str(h.risk) + "\n"
riskedit = risk.encode('utf-8')
conn.send(riskedit)

b = "Cost: " + str(h.cost) + "\n"
bbytes = b.encode('utf-8')
conn.send(bbytes)

c = "Mean of attack path lengths: " + str(h[0].mean_path_length()) + "\n"
cbytes = c.encode('utf-8')
conn.send(cbytes)

d = "Mode of attack path lengths: " + str(h[0].mode_path_length()) + "\n"
dbytes = d.encode('utf-8')
conn.send(dbytes)

e = "Shortest attack path length: " + str(h[0].shortest_path_length()) + "\n"
ebytes = e.encode('utf-8')
conn.send(ebytes)

f = "Return of Attack: " + str(h[0].return_on_attack()) + "\n"
fbytes = f.encode('utf-8')
conn.send(fbytes)

g = "Probability of attack success: " + str(h[0].probability_attack_success()) + "\n"
gbytes = g.encode('utf-8')
conn.send(gbytes)

#conn.send("Cost: " + str(h.cost) + "\n")
#conn.send("Mean of attack path lengths: " + str(h[0].mean_path_length()) + "\n")
#conn.send("Mode of attack path lengths: " + str(h[0].mode_path_length()) + "\n")
#conn.send("Shortest attack path length: " + str(h[0].shortest_path_length()) + "\n")
#conn.send("Return of Attack: " + str(h[0].return_on_attack()) + "\n")
#conn.send("Probability of attack success: " + str(h[0].probability_attack_success()) + "\n")



conn.close()
s.close();



