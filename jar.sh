cd compiled
jar cfe ../Myjar.jar Main *
cd ..
cd views
jar -uf ../Myjar.jar *
cd ..
cd assets
jar -uf ../Myjar.jar *
cd ..
jar -uf Myjar.jar LICENSE