echo "Quel type de partie voulez-vous lancer ?"

select choice in 'Une seule partie' '1000 parties'
do
  case $choice in
    'Une seule partie')
      mvn exec:java -Dexec.args="--single"
      break
      ;;

    '1000 parties')
    mvn exec:java -Dexec.args="--multiple"
    break
    ;;

    *)
    echo "Choix invalide. Vous devez entrer 1 ou 2."
    break
    ;;
  esac
done

read -rsp $'Press any key to continue...\n' -n1 key