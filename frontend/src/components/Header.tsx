import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";
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

  return (
    <Div>
      <Anchor onClick={() => navigate("/")}>
        <Icon name="LogoMedium" color="neutralTextStrong" />
      </Anchor>
      <UtilityMenu>
        <Button
          width={40}
          height={40}
          size="L"
          buttonType="Container"
          background="neutralTextStrong"
          onClick={onClickThemeMode}
        >
          {themeMode === "LIGHT" ? (
            <Icon name="Moon" color="neutralSurfaceDefault" />
          ) : (
            <Icon name="Sun" color="neutralSurfaceDefault" />
          )}
        </Button>
        <img
          style={{ width: "32px" }}
          src="https://avatars.githubusercontent.com/u/41321198?v=4"
        />
      </UtilityMenu>
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

const UtilityMenu = styled.div`
  display: flex;
  align-items: center;
  gap: 24px;
`;
