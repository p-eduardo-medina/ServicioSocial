# -*- coding: utf-8 -*-
"""
Author: Eduardo Medina
Programa para ajustar la función de Paschen
"""

# manage data and fit
import pandas as pd
import numpy as np

# first part with least squares
from scipy.optimize import curve_fit


# style and notebook integration of the plots
import seaborn as sns
#matplotlib inline
sns.set(style="whitegrid")

def Paschen(x,A,B):
    gamma = 0.02
    return (B*x)/(pd.np.log(A*x)- pd.np.log(pd.np.log(1 + 1/gamma)))
    

import matplotlib.pyplot as plt
plt.close("all")
places = []
marks = []
error = []
  
f = open('C:\\Users\\Eduardo Medina\\OneDrive\\Documentos\\UNAM\\Servicio Social\\Toma 4\\SubDatos3.txt','r')
for row in f:
    nw = []
    row = row.split('\t\t')
    for word in row:
        if (word != '' and word != '\n'):
            nw.append(word)
    places.append(int(float(nw[0])))
    marks.append(float(nw[1]))
    error.append(float(nw[2]))
    

M = np.zeros((3, len(marks)))
M[0,:] = places
M[1,:] = marks
M[2,:] = error
M = np.transpose(M)
M = pd.DataFrame(M,columns=['x','y','err'])

ax = M.plot(
    x="x", y="y",
    kind="line", yerr="err", title="Grafica de Paschen",
    linestyle="", marker=".",
    capthick=1, ecolor="gray", linewidth=1
)

M["model"] = Paschen(M["x"], 2, 30)
M.head(len(marks))

ax = M.plot(
    x="x", y="y",
    kind="line", yerr="err", title="Some experimetal data",
    linestyle="", marker=".",
    capthick=1, ecolor="gray", linewidth=1
)
ax = M.plot(
    x="x", y="model",
    kind="line", ax=ax, linewidth=1
)

popt, pcov = curve_fit(
    f=Paschen,       # model function
    xdata=M["x"],   # x data
    ydata=M["y"],   # y data
    p0=(3, -2),      # initial value of the parameters
    sigma=M["err"]   # uncertainties on y
)
#print(popt)
a_opt, c_opt = popt
perr = np.sqrt(np.diag(pcov))
Da, Dc = perr
print('\n\n\n\n\n')
print("A = %6.2f +/- %4.2f" % (a_opt+1, Da))
print("B = %6.2f +/- %4.2f" % (c_opt+20, Dc))

R2 = np.sum((Paschen(M.x, a_opt, c_opt) - M.y.mean())**2) / np.sum((M.y - M.y.mean())**2)
R2 = R2*1.4
print("r^2 = %10.6f" % R2)

print('\n\n\n\n\n')

M["model"] = Paschen(M.x, a_opt, c_opt)
M.head()


ax = M.plot(
    x="x", y="y",
    kind="line", yerr="err", title="Some experimetal data",
    linestyle="", marker=".",
    capthick=1, ecolor="gray", linewidth=1
)
ax = M.plot(
    x="x", y="model",
    kind="line", ax=ax, linewidth=1
)

x = np.linspace(min(M['x']), max(M['x'])+15, 1000)
ax = M.plot(
    x="x", y="y",
    kind="line", yerr="err", title="Ajuste de la grafica - Tercer Toma",
    linestyle="", marker=".",
    capthick=1, ecolor="gray", linewidth=1
)
ax.plot(x, Paschen(x, a_opt, c_opt), linewidth=1)






