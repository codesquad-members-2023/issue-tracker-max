import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";
import { clearAuthInfo } from "../utils/localStorage";
import { Avatar } from "./Avatar";
import { Button } from "./Button";
import { Icon } from "./icon/Icon";

export function Header({
  themeMode,
  onClickThemeMode,
}: {
  themeMode: "LIGHT" | "DARK";
  onClickThemeMode: () => void;
}) {
  const navigate = useNavigate();

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
        {themeMode === "LIGHT" ? (
          <Icon
            name="Moon"
            color="neutralTextStrong"
            onClick={onClickThemeMode}
          />
        ) : (
          <Icon
            name="Sun"
            color="neutralTextStrong"
            onClick={onClickThemeMode}
          />
        )}
        <Avatar
          size="L"
          src="https://avatars.githubusercontent.com/u/41321198?v=4"
        />
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
