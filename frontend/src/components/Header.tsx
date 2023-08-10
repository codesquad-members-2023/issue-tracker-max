import { useNavigate } from "react-router-dom";
import { styled } from "styled-components";
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
  gap: 40px;
`;
