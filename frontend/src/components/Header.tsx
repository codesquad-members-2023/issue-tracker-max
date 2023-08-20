import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";
import { clearAuthInfo, getUserInfo } from "../utils/localStorage";
import { Avatar } from "./Avatar";
import { Button } from "./Button";
import { Toggle } from "./ToggleButton";
import { Icon } from "./icon/Icon";

export function Header({ changeThemeMode }: { changeThemeMode: () => void }) {
  const navigate = useNavigate();
  const userInfo = getUserInfo();

  const logOut = () => {
    clearAuthInfo();
    navigate("/auth");
  };

  return (
    <Div>
      <Anchor onClick={() => navigate("/")}>
        <Icon name="LogoMedium" color="neutralTextStrong" />
      </Anchor>
      <HeaderRight>
        <Toggle onClick={changeThemeMode} />
        <Avatar size="L" src={userInfo.avatarUrl} userId={userInfo.loginId} />
        <Button size="S" buttonType="Ghost" onClick={logOut}>
          로그아웃
        </Button>
      </HeaderRight>
    </Div>
  );
}

const Div = styled.div`
  width: 1280px;
  display: inline-flex;
  padding: 27px 80px;
  justify-content: space-between;
  align-items: center;
`;

const Anchor = styled.a`
  cursor: pointer;
`;

const HeaderRight = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
`;
