import harmat as hm
import socket
import sys

def main():

    #Open a server to communicate with GUI
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    s.bind(("localhost", int(sys.argv[1])))
    s.listen(1)
    end = "c"
    conn, addr = s.accept()

    datas=[];
    #Receive data from the Cient
    while 1:
        data = conn.recv(1024)
        #conn.sendall(data)
        
        datas = datas + (data.decode('utf-8').splitlines())
        if end in data.decode('utf-8'):
            break    

    #print(datas)

    #Set variables
    numberOfNodes = int(datas[0])
    numberOfArcs = int(datas[1])
   
    # initialise the harm
    h = hm.Harm()

    # create the top layer of the harm
    # top_layer refers to the top layer of the harm
    h.top_layer = hm.AttackGraph()
    #print(float(datas[4+numberOfNodes*2]))

    #Create nodes
    hosts = [hm.Host(str(i)) for i in range(numberOfNodes)]
    #Make basic attack tree on each node
    i = 0
    for host in hosts:
        
        if int(host.name) is int(datas[2]):
            continue
        else:        
            host.lower_layer = hm.AttackTree()
            #Make a vulnerability with some metrics
            vulnerability1 = hm.Vulnerability('CVE-0000', values = {
                'risk' : float(datas[4+numberOfArcs*2 + (i*4)]),
                'cost' : float(datas[4+numberOfArcs*2+1 + (i*4)]),
                'probability' : float(datas[4+numberOfArcs*2+2 + (i*4)]),
                'impact' : float(datas[4+numberOfArcs*2+3 + (i*4)])
            })
            
            host.lower_layer.basic_at([vulnerability1])
            i+=1
        

    #Set a attacker
    hosts[int(datas[2])] = hm.Attacker()

    #Add Arcs between nodes based on the data from the Client
    for x in range (4, len(datas)-1-(4*numberOfArcs), 2):
        #print(int(datas[x]))
        #print(int(datas[x+1]))
        h[0].add_edge(hosts[int(datas[x])], hosts[int(datas[x+1])])
   
    #Set the attacker and target
    h[0].source = hosts[int(datas[2])]
    h[0].target = hosts[int(datas[3])]

    #Flow up
    h.flowup()

    #Run some metrics
    hm.HarmSummary(h).show()

    #Send analysis to the Client
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

    h = "Standard Deviation of attack path lengths: " + str(h[0].stdev_path_length()) + "\n"
    hbytes = h.encode('utf-8')
    conn.send(hbytes)

    conn.close()
    s.close();

if __name__ == '__main__':
    main()


