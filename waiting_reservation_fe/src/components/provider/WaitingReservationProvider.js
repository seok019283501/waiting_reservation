import {  useRef, useState } from "react";
import WaitingReservationContext from "./WaitingReservationContext";
import axios from "axios";
import Alerts from "../Alerts";
import '../../styles/Alerts.css'
const WaitingReservationProvider = (props) =>{
  const [role,setRole] = useState("ROLE_USER");
  const [reaminCount, setReaminCount] = useState("");
  const [userList,setUserList] = useState([]);
  const [orderMenuList,setOrderMenuList] = useState([]);
  const [username,setUsername] = useState("");
  const [name,setName] = useState("");
  const handleUsername = (id,name) =>{
    setUsername(id);
    setName(name)
  }


  //대기자 조회
  const waitingUserList = (id) =>{
    const tk = localStorage.getItem("jwt")

    axios.get((`http://localhost:8080/api/reservation/list/${id}`),{
      headers:{
        Authorization: tk
      }
    }).then(res=>{
      setUserList(res.data.body);
      setReaminCount(res.data.body.length);
    }).catch(err=>{
      console.log(err);
    })
  }
  
  const insertRole = role =>{
    setRole(role)
  }
  // 메뉴 추가
  const handleSetOrderMenuList=(item)=>{
    orderMenuList.push(item);
    setOrderMenuList(orderMenuList);
    handleMenuAddAlert(item)
  }
  const handleResetOrderMenuList = ()=>{
    setOrderMenuList([]);
  }


  //알림
  const [alertContent,setAlertContent] = useState("");
  //성공
  const alertMenuAddtRef = useRef("");

  const handleMenuAddAlert = (item) =>{
    setAlertContent(`${item.title} : ${item.count}개\n추가`);
    alertMenuAddtRef.current.classList.add("visible");
    setTimeout(()=> {
      alertMenuAddtRef.current.classList.remove("visible");
    }, 2000);
  }

  return (
    <WaitingReservationContext.Provider value={{insertRole, role,reaminCount,userList,waitingUserList,handleSetOrderMenuList,orderMenuList,handleResetOrderMenuList,handleUsername,username,name}}>
        <Alerts inputRef={alertMenuAddtRef} contents={alertContent}/>
        {props.children}
    </WaitingReservationContext.Provider>
  );
};

export default WaitingReservationProvider;