case "$OSTYPE" in
	msys*)	start powershell.exe -Command "echo 'Lancement du serveur...'; mvn -q spring-boot:run" ;;
	*)		if mvn spring-boot:run
			then
			  echo "Serveur lancé"
			  read -rsp $'Press any key to stop server\n' -n1
			else
			  echo "Echec du lancement du serveur"
			  read -rsp $'Press any key to continue...\n' -n1
			  exit 1
			fi
			;;
esac
