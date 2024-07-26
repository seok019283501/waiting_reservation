import { useContext, useEffect, useState } from "react";
import axios from "axios";
import "../../styles/LeftSide.css"
const LeftSide = (props) =>{
  //토큰
  const [token,setToken] = useState("");
  //user 아이디
  const [id, setId] = useState("");
  const [password,setPassword] = useState("")
  //user 이름
  const [name, setName] = useState("");

  //로그아웃
  const logout = ()=>{
    setName("");
    setToken("");
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
                  <div>회원가입</div>
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
              <div>웨이팅 정보</div>
            </div>
          </div>
          
        </div>
      </div>
    </>
  );
}

export default LeftSide;