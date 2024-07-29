import { useContext, useEffect, useRef, useState } from "react";
import axios from "axios";
import "../../styles/LeftSide.css"
import { useNavigate } from "react-router-dom";
import WaitingReservationContext from "../provider/WaitingReservationContext";
const LeftSide = (props) =>{

  const {insertRole, role} = useContext(WaitingReservationContext);
  //토큰
  const [token,setToken] = useState("");
  //user 아이디
  const [id, setId] = useState("");
  const [password,setPassword] = useState("")
  //user 이름
  const [name, setName] = useState("");
  //역할

  const navigator = useNavigate();

  //로그아웃
  const logout = ()=>{
    setName("");
    setToken("");
    insertRole("ROLE_USER");
    navigator("/");
    localStorage.removeItem("jwt")
  }
  //아이디 입력
  const insertId = (e) => {
    setId(e.target.value);
  }
  const insertPassword = (e) =>{
    setPassword(e.target.value);
  }
  //로그인
  const login = ()=>{
    axios.post((`http://localhost:8080/open-api/user/login`),{
      username:id,
      password:password
    }).then(res=>{
      setName(res.data.body.name);
      const tk = `Bearer ${res.data.body.token}`
      localStorage.setItem("jwt",tk);
      setToken(tk);
      insertRole(res.data.body.role);
      console.log("asdff")
    }).catch(err=>{
      console.log(err);
    })
  }

  //회원정보 요청
  useEffect(()=>{
    const tk = localStorage.getItem("jwt");
    setToken(tk);
    axios.get((`http://localhost:8080/api/user/info`),
    {
      headers:{
        Authorization: tk
      }
    }).then(res=>{
      console.log(res)
      setName(res.data.body.name);
      insertRole(res.data.body.role);
      console.log("asdff")
    }).catch(err=>{
      console.log(err);
    })
    
  },[])


  return (
    <>
      <div className="LeftSide-container">
        <div className="LeftSide-main-container">
          <div className="LeftSide-login-container">
            {
              token === '' || token === null ? 
              (<>
                <div className="LeftSide-login-input-continaer">
                  <input type="text" placeholder="id" onChange={(e)=>{insertId(e)}} className="LeftSide-login-input"/>
                  <input type="text" placeholder="password" onChange={(e)=>{insertPassword(e)}} className="LeftSide-login-input"/>
                </div>
                <div className="LeftSide-login-button-container" onClick={()=>{login()}}>
                  <div className="login-btn">로그인</div>
                </div>
                <div className="LeftSide-register-container">
                  <div onClick={()=>navigator("/regist")}>회원가입</div>
                </div>
              </>
                
              ) :(
              <>
                <div className="name">
                  {name}님 환영합니다.
                </div>
                <div className="LeftSide-logout-container" onClick={()=>{logout()}}>
                  <div className="logout-btn" >로그아웃</div>
                </div>
              </>
                
              )
            }
            
          </div>
          <div className="LeftSide-menu-continer">
            <div className="LeftSide-menu-continer">
              <div>mypage</div>
              {
                role === "ROLE_USER" ? <div onClick={()=>{navigator('/waiting')}}>웨이팅 정보</div> : null
              }
              {
                role === "ROLE_OWNER" ? <div onClick={()=>{navigator('/store/regist')}}>식당 등록</div> : null
              }
              
              
            </div>
          </div>
          
        </div>
      </div>
    </>
  );
}

export default LeftSide;